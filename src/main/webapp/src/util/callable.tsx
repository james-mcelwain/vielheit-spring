export type Callable<F extends Function> = F

export type ToCallabe<C, F extends Function> = (klass: C, fn: F) => C & Callable<F>

export function toCallable<C, F extends Function>(klass: C, fn: F): C & Callable<F> {
  return Object.assign<Callable<F>, C>(fn, klass)
}
