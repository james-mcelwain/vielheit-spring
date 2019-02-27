import {action, computed, observable} from 'mobx';
import http from './http';

export class State {
    @observable
    public loggedIn = false;
    @observable
    public user: object | null = null;

    @computed
    get token() {
        return localStorage.getItem('token')
    }

    constructor() {
        this.loggedIn = !!this.token;

        if (this.loggedIn) {
            const userId = localStorage.getItem("userId");
            http.get(`/user/${userId}`).then(({ data }) => {
                this.user = data
            })
        }
    }

    @action
    public async logIn(username: String, password: String) {
        try {
            const { data } = await http.post('/user/auth', {
                username,
                password
            });
            const { token, user } = data;
            this.loggedIn = true;
            this.user = user;
            localStorage.setItem("userId", user.id);
            localStorage.setItem('token', token);
        } catch (error) {
            console.error(error)
        }
    }
}