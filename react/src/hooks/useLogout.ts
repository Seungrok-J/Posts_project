import { toast } from 'react-toastify';
import useUserStore from '../store/useUserStore';
import { PATH } from '../constants/paths';
import { logout } from '../api/auth';
import {useNavigate} from "react-router-dom";

const useLogout = () => {
	const logoutStore  = useUserStore(state => state.logout);
	const navigate = useNavigate();
	const handleLogout = async () => {
		try {
			await logout();
			document.cookie = "Session_ID=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
			logoutStore();
			toast.success('Logged out successfully');
			setTimeout(() => {
				navigate(PATH.HOME);
			}, 5000);
		} catch (error) {
			console.error('Logout failed:', error);
			toast.error('Logout failed. Please try again.');
		}
	};

	return handleLogout;
};

export default useLogout;