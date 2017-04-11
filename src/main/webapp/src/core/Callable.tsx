/**
 * F represent a function of any type that will be grafted on to our callable class.
 * As such the "this" context of the function cannot be guaranteed. We attempt to bind below
 * but the type signature itself does not provide a "this," just callable semantics.
 *
 * If "this" context needs to be available, a non-arrow function should be passed to guarantee a bound
 * function is passed post-transpilation.
 */
export type Callable<F extends Function> = F

/**
 * Object#assign will map the properties of
 */
export function toCallable<C, F extends Function>(klass: C, fn: F): C & Callable<F> {
  return Object.assign<Callable<F>, C>(fn.bind ? fn.bind(klass) : fn, klass)
}
