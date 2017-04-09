export interface Callable<A, T> {
  (a?: A): T
}

export type ToCallabe<C, A, T> = (klass: C, fn: (a: A) => T) => C & Callable<A, T>

export function toCallable<C, A, T>(klass: C, fn: (a?: A) => T): C & Callable<A, T> {
  return Object.assign<Callable<A,T>, C>(fn, klass)
}
