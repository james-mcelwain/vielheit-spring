export function Override(prototype: any, name: string, descriptor: PropertyDescriptor) {
  const baseType = Object.getPrototypeOf(prototype)
  if(typeof baseType[name] !== 'function') {
    throw new Error(`Method ${name} of ${prototype.constructor.name} does not override any base class method`)
  }
}
