#include <algorithm>
#include <cassert>
#include <cstdio>
#include <cstdint>
#include <vector>

typedef __int128 int128_t;

constexpr int128_t kNumCards    = 119315717514047ll;
constexpr int128_t kNumShuffles = 101741582076661ll;
constexpr int128_t kFinalPos = 2020;


static inline int128_t mod(int128_t a, int128_t b)
{
  return (a >= 0) ? (a % b) : (b + a % b);
}


static int128_t gcd_extended(int128_t a, int128_t b, int128_t& x, int128_t& y)
{
  if (a == 0) {
    x = 0;
    y = 1;
    return b;
  }

  int128_t x1, y1;
  int128_t gcd = gcd_extended(b % a, a, x1, y1);
  x = y1 - (b / a) * x1;
  y = x1;
  return gcd;
}


static int128_t modular_inverse(int128_t b, int128_t n)
{
  int128_t x, y;
  int128_t g = gcd_extended(b, n, x, y);
  return (g != 1) ? -1 : mod(x, n);
}


static int128_t modular_divide(int128_t a, int128_t b, int128_t n)
{
  a = mod(a, n);
  int128_t inv = modular_inverse(b, n);
  return (inv == -1) ? -1 : (a * inv) % n;
}


int128_t modular_power(int128_t base, int128_t exponent, int128_t n)
{
  assert(exponent >= 0);
  if (exponent == 0) {
    return (base == 0) ? 0 : 1;
  }

  int128_t bit = 1;
  int128_t power = mod(base, n);
  int128_t out = 1;
  while (bit <= exponent) {
    if (exponent & bit) {
      out = mod(out * power, n);
    }
    power = mod(power * power, n);
    bit <<= 1;
  }

  return out;
}


int main(int argc, char** argv)
{
  FILE* f = fopen(argv[1], "r");
  if (!f) {
    fprintf(stderr, "Failed to open %s\n", argv[1]);
    return 1;
  }

  int128_t A = 1;
  int128_t B = 0;
  char line[256];
  while (fgets(line, sizeof(line), f) != nullptr) {
    int64_t arg;
    int128_t a, b;
    if (sscanf(line, "cut %lld", &arg) == 1) {
      if (arg < 0) {
        arg += kNumCards;
      }
      a = 1;
      b = kNumCards - arg;
    }
    else if (sscanf(line, "deal with increment %lld", &arg) == 1) {
      a = arg;
      b = 0;
    }
    else {
      a = -1;
      b = kNumCards - 1;
    }
    A = mod(a * A, kNumCards);
    B = mod(a * B + b, kNumCards);
  }
  fclose(f);

  // A and B are coefficients that can be used to calculate where card C ends up:
  //   C' = mod(A * C + B, kNumCards);
  // We need to calculate the coefficients after N iterations.
  int128_t fullA = modular_power(A, kNumShuffles, kNumCards);
  int128_t fullB = mod(B * modular_divide(modular_power(A, kNumShuffles, kNumCards) - 1, A - 1, kNumCards), kNumCards);

  // Now do the inverse calculation, i.e. given C', what was C?
  int128_t startPos = mod(modular_divide(mod(kFinalPos - fullB, kNumCards), fullA, kNumCards), kNumCards);
  printf("The card at index %lld after %lld shuffles is %lld\n", int64_t(kFinalPos), int64_t(kNumShuffles), int64_t(startPos));
}
