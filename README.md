<h1 align="center">anoitos</h1>

<div align="center">

[![Latest Version](https://img.shields.io/github/v/release/ya-ilya/anoitos?logo=github)](https://github.com/ya-ilya/anoitos/releases/latest)

</div>

A simple programming language written in Kotlin

### TODO
- Add boolean and numeric operations
- Add more libraries and functions

### Examples
#### Factorial recursive function
```
import 'io', 'logic', 'math';

fun factorial(n) {
    if (or(equals(n, 0), equals(n, 1))) {
        return 1;
    } else {
        return multiply(n, factorial(minus(n, 1)));
    }
}
            
println(factorial(5));
```
Output: 120