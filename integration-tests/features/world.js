const { defineSupportCode } = require('cucumber')
const { create } = require('axios')

let sessionStorage
const makeSessionStorage = () => sessionStorage = {
  getItem(key) {
    return this[key]
  },
  setItem(key, item) {
    this[key] = item
  }
}

makeSessionStorage()

defineSupportCode(({ setWorldConstructor, When, Then, Before, After }) => {
  setWorldConstructor(function CustomWord() {
    global['expect'] = require('chai').expect
    global['http'] = create({
      baseURL: 'http://localhost:8080/api/',
      timeout: 10000,
    })

    http.interceptors.request.use((config) => {
      const token = sessionStorage.getItem('token')

      if (token) {
        config.headers = Object.assign({
          'X-Authorization': `Bearer ${token}`,
          'Cache-Control': `no-cache`,
        }, config.headers)
      }

      return config
    })
  })

  After({ tags: "@logout" }, function () {
    makeSessionStorage()
  })

  Before(function () {
    global['RES'] = null
    global['ERR'] = null
  })

  When('I {method:stringInDoubleQuotes} the path {path:stringInDoubleQuotes}', function (method, path) {
    return http[method.toLowerCase()](path)
      .then((response) => Reflect.set(global, 'RES', response))
      .catch(({ response }) => Reflect.set(global, 'ERR', response))
  })


  Then('the response is {status:int}', function (status) {
    expect(ERR && ERR.status, 'ERR STATUS').not.to.exist
    expect(RES.status).to.equal(status)
  })

  Then('the error response is {response:int}', function (status) {
    expect(ERR, 'ERR').to.exist
    expect(ERR.status).to.equal(status)
  })

  Then('the body is', function (string) {
    expect(ERR && ERR.status, 'ERR STATUS').not.to.exist
    expect(RES, 'RES').to.exist
    expect(RES.data).to.deep.equal(deserialize(string))
  })

  Then('the error body is', function (string) {
    expect(ERR, 'ERR').to.exist
    expect(ERR.data.message).to.deep.equal(deserialize(string))
  })

  Then('the errorCode is {code:int}', function (code) {
    expect(ERR, 'ERR').to.exist
    expect(ERR.data.errorCode).to.equal(code)
  })

  When('I log in', function () {
    return http.post('auth/login', {
      emailAddress: 'admin@vielhe.it',
      password: 'test1234'
    }).then(({ data }) => {
      expect(data.token).to.exist
      sessionStorage.setItem('token', data.token)
    })
      .catch((err) => {
        throw new Error(err)
      })
  })

  When('I log in with the credentials {emailAddress:stringInDoubleQuotes} / {password:stringInDoubleQuotes}', (emailAddress, password) => {
    return http.post('auth/login', {
      emailAddress,
      password
    }).then(({ data }) => {
      expect(data.token).to.exist
      sessionStorage.setItem('token', data.token)
    })
      .catch((err) => {
        throw new Error(err)
      })
  })

  When('I {method:stringInDoubleQuotes} to {url:stringInDoubleQuotes} with', function (method, url, string) {
    return http[method.toLowerCase()](url, JSON.parse(string))
      .then((response) => Reflect.set(global, 'RES', response))
      .catch(({ response }) => Reflect.set(global, 'ERR', response))
  })
})

function deserialize(string) {
  try {
    return JSON.parse(string)
  } catch (err) {
    throw new Error(err.message + ': ' + string.toString())
  }
}
