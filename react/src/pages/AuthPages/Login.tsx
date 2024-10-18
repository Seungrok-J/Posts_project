import React from "react";
import { motion } from 'framer-motion';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import LoginForm from '../../components/Auth/LoginForm'
import useLogin from '../../hooks/useLogin';

const Login: React.FC = () => {
	const { formData, setFormData, error, handleLogin } = useLogin();

	const handleEmailChange = (email: string) => {
		setFormData(prev => ({ ...prev, email }));
	};

	const handlePasswordChange = (password: string) => {
		setFormData(prev => ({ ...prev, password }));
	};

	return (
		<motion.div
			initial={{opacity: 0}}
			animate={{opacity: 1}}
			transition={{duration: 0.5}}
			className="flex items-center justify-center min-h-screen"
		>
			<div className="p-10 rounded-xl shadow-2xl max-w-md w-full">
				<ToastContainer/>
				<motion.h1
					initial={{y: -20}}
					animate={{y: 0}}
					className="text-center text-3xl font-bold mb-8"
				>
					로그인
				</motion.h1>
				<LoginForm
					email={formData.email}
					setEmail={handleEmailChange}
					password={formData.password}
					setPassword={handlePasswordChange}
					handleSubmit={handleLogin}
					error={error}
				/>
			</div>
		</motion.div>
	);
};


export default Login;