import axios, {AxiosRequestConfig, AxiosTransformer} from 'axios';

const config: AxiosRequestConfig = {
    baseURL: 'http://localhost:8080',
};

const http = axios.create(config);

http.interceptors.request.use((config: AxiosRequestConfig) => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers['Authorization'] = `Bearer ${token}`
    }

    return config
});

export default http;

