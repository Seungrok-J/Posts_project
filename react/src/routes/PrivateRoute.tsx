import React from 'react';
import { Navigate } from 'react-router-dom';
import useUserStore from '../store/useUserStore';
import { PATH } from '../constants/paths';

interface PrivateRouteProps {
	children: React.ReactNode;
}

const PrivateRoute: React.FC<PrivateRouteProps> = ({ children }) => {
	const isLoggedIn = useUserStore(state => state.isLoggedIn);

	if (!isLoggedIn) {
		return <Navigate to={PATH.LOGIN} replace />;
	}

	return <>{children}</>;
};

export default PrivateRoute;