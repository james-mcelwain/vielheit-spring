export default class User {
  public firstName: string
  public lastName: string
  public emailAddress: string
  public id: number

  constructor(user: User | string | null) {
    if (user !== null) {
      if (typeof user === 'string') {
        user = JSON.parse(user) as User
      }

      this.firstName = user.firstName
      this.lastName = user.lastName
      this.emailAddress = user.emailAddress
      this.id = user.id
    }
  }

  public fullName() {
    return `${this.firstName} ${this.lastName}`
  }
}
