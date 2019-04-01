# Contributing

If you'd like to contribute you'd be more than welcome. The code base is ever growing and in order to make it usable by as many people as possible, we'd like to hear your ideas, suggestions, feedback, as well as accepting your code improvements.

Join us on the Gitter.im channel (chat link at the top of the main README.md file)

If you'd like to contribute code, fork the repo, make your changes, then create a pull request back into the irufus/gdax-java codebase. Your code will then be reviewed prior to merging it in.

Please ensure where possible, tests are included with any new features. Tests in this project are JUnit. Mockito, PowerMockito, etc. are all possible. The only real restriction is to Keep It Simple (where possible).

Please also update/amend the README.md as necessary regarding changes so that it is kept up to date.

# SPRING BOOT - Dependency injection and auto wiring

Spring is great. Having developed for many years now, developers on this project have built applications large and small. Simply put without Spring/DI, the skill necessary to develop an application of this size becomes messy/chaotic due to the complexity that comes with creating large codebases with many dependencies. New-ing up classes to configure other classes, details getting lost in the code. Etc. Its all considerably easier using the Spring framework.

Spring helps alleviate all the spaghetti configuration. I'll give a high level overview of Spring concepts utilised in this codebase.

Spring Beans - Java instances. Beans are slightly different to POJOs as the lifecycle of a bean is managed by spring - from instantiation to termination.

`@Component` - whenever you write a class, marking it with the @Component annotation makes a class instance available in the Spring Context (essentially a registry of instances of various objects). Any instance in the spring context can be fetched and passed in to a constructor at instantiation using the `@Autowired` Annotation. `@Components` must be configured correctly at instantiation. After that changing them is done through event driven processes (buttons/keyboard) which you will have to write code for.
`@Autowired` - Used to get instances of a particular object at instantiation time. A constructor or field variable marked with `@Autowired` will have an instance of the class provided so long as one (and only one) exists in the Spring Context. To get a Java object into the Spring Context, mark it with `@Component`
`@SpringBootConfiguration` - tells Spring there's some configured items in the class - Beans/factories/etc.
`@SpringBootApplication` - used on the application entry point. Tells Spring where to begin from - usually some top level class file.
`@Value` - used heavily to pass properties from application.yml files, into the constructors of various classes. If we want to configure a class with a true/false switch we can just declare a new true/false variable in the application.yml called, say, `gdax.isEnabled`, set it to `true`, then when we want to pass it into a constructor we just write a constructor param and annotate it with `@Value("${gdax.isEnabled}") boolean isEnabled` and isEnabled in our code will pick up the value that is set in the config. Since .yml files are structured, we can group elements together more sensibly than a standard properties file. The only time Spring/yml struggles with config in a file is when we declare a list. We can cross that bridge if/when we get to it, For now its not a concern but something to be aware of.

Spring boot also makes our lives easier by allowing us to set a testing profile that differs from the live configuration.

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

Note Dependency injection is far more predictable in spring if constructor injection is used (i.e. put the @Autowired annotation on the constructor rather than a field in your class).

```
@Component
public class NewComponent {

    @Autowired
    public NewComponent() {
        // put object into a consistent state ready for interacting with.
    }
}
```

Now if you want to utilise some of the already implemented features of this codebase like getting your account details, you can autowire in the already existent (@)component `AccountService`

```
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

```
    ConfigurableApplicationContext ctx = new SpringApplicationBuilder(GdaxApiApplication.class)
                .headless(false).run(args);

    NewComponent newComponent = ctx.getBean(newComponent.class);
    newComponent.printMyAccounts();
    ... etc.
```