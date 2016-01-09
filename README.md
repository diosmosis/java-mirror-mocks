# java-mirror-mocks

## Description

A set of mock classes for the interfaces in the `javax.lang.model` package. For use in unit testing
code in annotation processors.

## Usage

The implementations provided by this package use reflection to mimic the mirror API as closely as
possible. So to use it during tests, first create a test class that you'd want a `Element` for, eg:

```
@MyAnnotationToProcess
public class TestClassWithAnnotation {
    // ...
}
```

then create the `Element` using `ElementFactory`:

```
import flarestar.mirror.mock.element.ElementFactory;

public class MyAnnotationProcessorComponentTest {

    private MyAnnotationProcessorComponent instance = new MyAnnotationProcessorComponent();

    @Test
    public void testSomething() {
        TypeElement mockElement = ElementFactory.make(TestClassWithAnnotation.class);

        instance.doSomething(mockElement);
    }
```

This should result in viable unit tests w/o the verbosity that comes hand in hand w/ using
mocking frameworks like `mockito`.

## Caveat

The mirror API is very strange. There are many corner cases that are either impossible to replicate
via reflection or are cases where I am not positive what the actual behavior should be. So in some
cases these mocks may not behave as expected.

It is thus recommended to couple unit tests w/ integration tests that invoke annotation processors
through the compiler API. This way, you can get a larger amount of code coverage and the confidence for
success in the real world from integration tests, plus easier debugging when performing refactoring from
your unit tests.