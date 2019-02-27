import {action, computed, observable} from 'mobx';
import http from './http';

export class AppState {
    @observable
    public loggedIn = false;

    @computed get token() {
        return localStorage.getItem('token')
    }

    @action
    public async logIn(username: String, password: String) {
        try {
            const { data } = await http.post('/user/auth', {
                username,
                password
            });
            localStorage.setItem('token', data)
            this.loggedIn = true
        } catch (error) {
            console.log(':(', error)
        }
    }
}