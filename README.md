# keywords

asm-keywords implements a few different (statistical and NLP) methods for extracting keywords from text.


## Installation

`git clone git@github.com:assemblymade/keywords.git`.


## Hacking

`lein deps; lein ring server`


## Examples

```clojure
(use 'keywords.core)

(rake "I am a string of text. It's not that I have a point exactly, I'm just an example.")
```


### Bugs

Open an [issue](https://github.com/assemblymade/keywords/issues)

## License

Copyright © 2015 Assembly, Inc.

[![AGPL v3.0](https://img.shields.io/badge/license-AGPL%20v3.0-blue.svg)](http://opensource.org/licenses/AGPL-3.0)
