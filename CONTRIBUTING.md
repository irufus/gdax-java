# Contributing

If you'd like to contribute you'd be more than welcome. The code base is ever growing and in order to make it usable by as many people as possible, we'd like your ideas, suggestions, feedback, as well as accepting your code improvements.

Join us on the Gitter.im channel (chat link at the top of the main README.md file)

If you'd like to contribute code, fork the repo, make your changes, then create a pull request back into the irufus/gdax-java codebase. Your code will then be reviewed prior to merging it in.

Please ensure where possible, tests are included with any new features. Tests in this project are JUnit. Mockito, PowerMockito, etc. are all possible. The only real restriction is to Keep It Simple (where possible).

Please also update/amend the README.md as necessary regarding changes so that it is kept up to date.

# TESTING

Tests act as documentation for the code, demonstrating actual usage.
If you're not familiar with TDD then perhaps now is a great time to begin.
Test Driven Development will:
- help determine what to build
- help you to prioritise which elements need to be in place first
- help you to implement a solution using the minimal amount of code
- the design of your code should emerge from the tests

Not testing code leads to poor implementations that are hard to read, debug and are typically unmaintainable.


If you'd like to contribute to the codebase, your code must be robust. To ensure its Robust you must provide tests that cover the scenarios your solution should handle.

Currently tests follow a given/when/then approach. That is:
- `given` some setup and preconditions
- `when` I invoke method X on some object under test
- `then` some results should be expected.

You'll spot this pattern clearly in the test code because most of the precondition code is grouped together, then there's a separated line calling to some method on the testObject (typically), and finally a group of assertions at the end of the test. These three sections are typically grouped using new lines as separators.

Test naming - typically tests are named in the following way: `shouldDoXWhenY`.

The best example to follow in this codebase is probably the `LiveOrderBookTest`

If you spot a bug, write a test for it to highlight the issue, create a fix and submit a pull request for review.

Code that has very little in the way of testing is more likely to be rejected.

The current codebase tests what is sensible and possible to test. Mainly getting things. Testing items are submitted to the server isn't ideal as the GDAX sandbox no longer exists so it isn't particularly safe to do this especially for placing orders.

# I want to create some new element in the codebase

Where do you start?

Add this template, add the relevant tests you want, then begin implementing your code.

Note Dependency injection is far more predictable in spring if constructor injection is used (i.e. put the @Autowired/@Inject annotation on the constructor rather than a field in your class).

```java
@Component
public class NewComponent {

    @Autowired
    public NewComponent() {
        // put object into a consistent state ready for interacting with.
    }
}
```

Now if you want to utilise some of the already implemented features of this codebase like getting your account details, you can autowire in the already existent (@)component `AccountService`

```java
@Component
public class NewComponent {

    private AccountService accountService;

    @Autowired
    public NewComponent(AccountService accountService) {
        this.accountService = accountService;
    }

    public void printMyAccounts() {
        List<Account> accounts = accountService.getAccounts();
        for(Account account: accounts) {
            System.out.println(accounts.getBalance());
    }
}
```

You can now add more code to the main method of this codebase and interact with your new code. You'll need a reference to the new code which can be obtained with:

```java
    ConfigurableApplicationContext ctx = new SpringApplicationBuilder(GdaxApiApplication.class)
                .headless(false).run(args);

    NewComponent newComponent = ctx.getBean(newComponent.class);
    newComponent.printMyAccounts();
    ... etc.
```