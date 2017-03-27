#ifndef UTIL_H_
#define UTIL_H_

#define sb(reg, pin) (reg |= (1<<pin))
#define cb(reg, pin) (reg &= ~(1<<pin))

#endif