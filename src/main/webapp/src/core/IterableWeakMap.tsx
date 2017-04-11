/**
 * If the only reference is in keys, then the key will live for the lifetime
 * of the instance that contains it, but if the instance drops out, so will
 * all the keys only held by instance.
 *
 * Obviously, this should not be used for scenarios in which access to the key
 * *should* be dependent on the lifetime of the key, but rather provides a
 * generic iterable map where the keys can be anything that extends object.
 */
export class IterableWeakMap<K extends object, V> extends WeakMap<K, V> implements Iterable<V> {

  private _keys: Array<K> = []

  constructor() {
    super()
  }

  public keys(): Array<K> {
    return this._keys
  }

  public find(fn: (k: K) => boolean): V | undefined {
    const key = this._keys.find(fn)
    if (key) {
      return this.get(key)
    }
  }

  public [Symbol.iterator](): Iterator<V> {
    return this._keys.reduce((xs, x) => {
      const v = this.get(x)
      if (v) {
        xs.push(v)
      }

      return xs
    }, [] as Array<V>).values()
  }

  public set(k: K, v: V): this {
    super.set(k, v)
    this._keys.push(k)
    return this
  }

  public delete(k: K): boolean {
    const d = super.delete(k)
    if (!d) {
      return d
    }
    this._keys.splice(this._keys.findIndex((x) => x === k), 1)
    return d
  }

}
