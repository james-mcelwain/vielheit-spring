export default class User {
  get firstName() {
    return this._firstName
  }

  get lastName() {
    return this._lastName
  }

  get emailAddress() {
    return this._emailAddress
  }

  get id() {
    return this._id
  }

  get fullName() {
    return `${this._firstName} ${this._lastName}`
  }

  constructor(user) {
    this._firstName = user.firstName
    this._lastName = user.lastName
    this._emailAddress = user.emailAddress
    this._id = user.id
  }
}
