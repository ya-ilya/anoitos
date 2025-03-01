<h1 align="center">anoitos</h1>

<div align="center">

[![Latest Version](https://img.shields.io/github/v/release/ya-ilya/anoitos?logo=github)](https://github.com/ya-ilya/anoitos/releases/latest)

</div>

A simple programming language written in Kotlin

### TODO
- ~~Add boolean and numeric operations~~
- Add more libraries and functions
- Implement `EQUALS` operation
- Implement lists
- Implement comments
- Split `NUMBER` by `INT` and `DOUBLE`
- Add more tests

### Examples

#### Factorial recursive function
```
import 'io', 'logic';

fun factorial(n) {
    if (equals(n, 0) || equals(n, 1)) {
        return 1;
    } else {
        return n * factorial(n - 1);
    }
}

println(factorial(5));
```
Output: 120.0

#### Fibonacci recursive function
```
import 'logic';

fun fibonacci(n) {
    if (equals(n, 0)) {
        return 0;
    } elif (equals(n, 1)) {
        return 1;
    } else {
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}

var result = fibonacci(10);
```
Output: 55.0

#### Decimal to binary recursive function
```
import 'logic';

fun decimalToBinary(n) {
    if (equals(n, 0)) {
        return n;
    } else {
        return (n % 2 + 10 * decimalToBinary(n // 2));
    }
}

var result = decimalToBinary(10);
```
Output: 1010.0