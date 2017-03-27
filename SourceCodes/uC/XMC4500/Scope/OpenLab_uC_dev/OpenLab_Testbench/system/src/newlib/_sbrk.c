//
// This file is part of the µOS++ III distribution.
// Copyright (c) 2014 Liviu Ionescu.
//

// ----------------------------------------------------------------------------

#include <sys/types.h>
#include <errno.h>

// ----------------------------------------------------------------------------

caddr_t
_sbrk(int incr);

// ----------------------------------------------------------------------------

// The definitions used here should be kept in sync with the
// stack definitions in the linker script.

caddr_t
_sbrk(int incr)
{
  extern char Heap_Bank1_Start; // Defined by the linker.
  extern char Heap_Bank1_End; // Defined by the linker.

  static char* current_heap_end;
  char* current_block_address;

  if (current_heap_end == 0)
    {
      current_heap_end = &Heap_Bank1_Start;
    }

  current_block_address = current_heap_end;

  // Need to align heap to word boundary, else will get
  // hard faults on Cortex-M0. So we assume that heap starts on
  // word boundary, hence make sure we always add a multiple of
  // 4 to it.
  incr = (incr + 3) & (~3); // align value to 4
  if (current_heap_end + incr > &Heap_Bank1_End)
    {
      // Some of the libstdc++-v3 tests rely upon detecting
      // out of memory errors, so do not abort here.
#if 0
      extern void abort (void);

      _write (1, "_sbrk: Heap and stack collision\n", 32);

      abort ();
#else
      // Heap has overflowed
      errno = ENOMEM;
      return (caddr_t) - 1;
#endif
    }

  current_heap_end += incr;

  return (caddr_t) current_block_address;
}

// ----------------------------------------------------------------------------

