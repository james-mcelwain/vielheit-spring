/**
 * A class that provides callable instances -- i.e. instances that share function call
 * semantics.
 *
 * Note, this implementation should *not* be considered stable. "Function" is described
 * by the ECMAScript specification as an "intrinsic object" -- i.e. native code -- found
 * as a property of the global object (window.Function).
 *
 * The specification notes regarding subclassing:
 *
 *  The Function constructor is designed to be subclassable. It may be used as
 *  the value of an extends clause of a class definition. Subclass constructors
 *  that intend to inherit the specified Function behaviour must include a super
 *  call to the Function constructor to create and initialize a subclass instances
 *  with the internal slots necessary for built-in function behaviour. All
 *  ECMAScript syntactic forms for defining function objects create instances of
 *  Function. There is no syntactic means to create instances of Function subclasses
 *  except for the built-in Generator Function subclass
 *
 * In Chrome, when super is called, a truly anonymous function is created. In order to
 * bind the "this" context of the subclass, it is necessary to return a bounded instance
 * of the superclass function from the subclass constructor.
 */
export abstract class Callable extends Function {
  constructor() {
    super('...args', 'return this.__call__(...args)')
    return this.bind(this)
  }

  private __call__() {
    throw new Error('__call__ must be defined in derived instance!')
  }
}

/**
 * Convenience method to allow an arbitrary method to be assigned to __call__
 * provided to help obscure implementation details.
 */
export function Call(prototype: any, name: string, descriptor: PropertyDescriptor) {
  if (prototype.__call__) {
    throw new Error(`Cannot create callable instance with method ${name}: __call__ already defined!`)
  }

  prototype.__call__ = prototype[name]
}

/**
 * A less sketchy way to provide a callable object, by mapping and binding properties
 * of a pre-existing class instance. *However*, it is important to note that this does
 * not preserve the class instance's nominal type -- i.e. `instanceof` is not preserved.
 *
 * F represent a function of any type that will be grafted on to our callable class.
 * As such the "this" context of the function cannot be guaranteed. We attempt to bind below
 * but the type signature itself does not provide a "this," just callable semantics.
 *
 * If "this" context needs to be available, a non-arrow function should be passed to guarantee a
 * bound function is passed post-transpilation.
 */
export function toCallable<C, F extends Function>(klass: C, fn: F): C & F {
  return Object.assign<F, C>(fn.bind ? fn.bind(klass) : fn, klass)
}
