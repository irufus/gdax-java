# Contributing

If you'd like to contribute you'd be more than welcome. The code base is ever growing and in order to make it usable by as many people as possible, we'd like your ideas, suggestions, feedback, as well as accepting your code improvements.

Join us on the Gitter.im channel (chat link at the top of the main README.md file)

If you'd like to contribute code, fork the repo, make your changes, then create a pull request back into the irufus/gdax-java codebase. Your code will then be reviewed prior to merging it in.

Please ensure where possible, tests are included with any new features. Tests in this project are JUnit. Mockito, PowerMockito, etc. are all possible. The only real restriction is to Keep It Simple (where possible).

Please also update/amend the README.md as necessary regarding changes so that it is kept up to date.

# TESTING

Tests act as documentation for the code, demonstrating actual usage.
If you're not familiar with TDD then now is a great time to begin!
Test Driven Development will:
- help determine what to build
- help you to prioritise which elements need to be in place first
- help you to implement a solution using the minimal amount of code
- frontload the stress of the design of your code, which should emerge from/be driven by the tests

Not testing code leads to poor implementations that are hard to read, debug and are typically unmaintainable.

If you'd like to contribute to the codebase, your code must be robust. To ensure its Robust you must provide tests that cover the scenarios your solution should handle.

Currently tests follow a given/when/then approach. That is:
- `given` some setup and preconditions
- `when` I invoke method X on some object under test
- `then` some results should be expected.

You'll spot this pattern clearly in the test code because most of the precondition code is grouped together, then there's a separated line calling to some method on the testObject (typically), and finally a group of assertions at the end of the test. These three sections are typically grouped using new lines as separators.

Test naming - typically tests are named in the following way: `shouldDoXWhenY`.

If you spot a bug, write a test for it to highlight the issue, create a fix, and submit a pull request for review.

Code that has very little in the way of testing is more likely to be rejected.

The current codebase tests what is sensible and possible to test. Mainly getting things.

## I want to create some new element in the codebase

Where do you start?

Add this template, add the relevant tests you want, then begin implementing your code.

Note Dependency injection is far more predictable in spring if constructor injection is used i.e. put the `@Autowired`/`@Inject` annotation on the constructor rather than a field in your class. 
If a class only has one constructor the `@Autowired` annotation is not necessary.

```java
@Component
public class NewComponent {

    @Autowired
    public NewComponent() {
        // put object into a consistent state ready for interacting with.
    }
}
```

If you want to utilise an existing service, you just need the configuration similar to below:

```java
@SpringBootConfiguration
public class ApplicationConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean
    public Signature signature(@Value("${exchange.secret}") String secret) {
        return new Signature(secret);
    }
    
    @Bean
    public CoinbaseExchange coinbasePro(@Value("${exchange.key}") String publicKey,
                                        @Value("${exchange.passphrase}") String passphrase,
                                        @Value("${exchange.baseUrl}") String baseUrl,
                                        Signature signature,
                                        ObjectMapper objectMapper) {
        return new CoinbaseExchangeImpl(publicKey, passphrase, baseUrl, signature, objectMapper);
    }

   /**
    * now you can create services by wiring in the CoinbaseExchange object to handle incoming/outgoing requests.
    **/

    @Bean
    public AccountService accountService(CoinbaseExchange exchange) {
        return new AccountService(exchange);
    }
} 
```

```java
@Component
public class MyApplication {

    private AccountService accountService;

    @Autowired
    public MyApplication(AccountService accountService) {
        this.accountService = accountService;
    }

    public void printMyAccounts() {
        List<Account> accounts = accountService.getAccounts();
        for(Account account: accounts) {
            System.out.println(accounts.getBalance());
        }
    }
}
```

If you can do TDD, great. If not, add it after you've coded a solution.

Tests all follow a "given, when, then" style... *given* some preconditions `X`, *when* I exercise a given method `Y` (typically a method call on its own line), *then* the results are expected to be `Z` (if not the test fails).