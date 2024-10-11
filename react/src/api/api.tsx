import axios,{AxiosError} from 'axios';

const api = axios.create({
	baseURL: 'http://localhost:8080/api',
	headers: {
		'Content-Type': 'application/json'
	}
});

export default api;