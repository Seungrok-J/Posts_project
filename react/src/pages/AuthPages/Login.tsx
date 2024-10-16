import React, {useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import {motion} from 'framer-motion';
import {toast, ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import {PATH} from '../../constants/paths'
import api from '../../api/api'
import useUserStore from "../../store/useUserStore";

const Login: React.FC = () => {
	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");
	const [error, setError] = useState('');
	const navigate = useNavigate();
	const { setUser } = useUserStore();

	const handleLogin = async (e: React.FormEvent) => {
		e.preventDefault();
		try {
			const response = await api.post('/auth/login',{ userEmail: email, password })
			if (response.status === 200) {
				console.log(response.data)
				setUser({
					userId: response.data.userId,  // 사용자 ID
					userName: response.data.userName, // 사용자 이름
					userNickName: response.data.userNickName, // 사용자 닉네임
					userEmail: response.data.userEmail, // 사용자 이메일
					sessionId: response.data.sessionId // 세션 ID
				});
				toast.success('Login Successful');
				console.log(setUser)
				setTimeout(() => {
					navigate(PATH.HOME);
				}, 2000);
			}
		} catch (err) {
			setError("Login failed. Please check your credentials");
			toast.error('Login failed')
		}

	};

	return (
		<>
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
					<form onSubmit={handleLogin} className="space-y-6">
						<div>
							<label htmlFor="email" className="block text-sm font-medium mb-1">
								이메일
							</label>
							<input
								id="email"
								type="email"
								placeholder="youremail@example.com"
								value={email}
								onChange={e => setEmail(e.target.value)}
								required
								className="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 transition duration-200 border-gray-300 focus:ring-blue-500"
							/>
						</div>
						<div>
							<label htmlFor="password" className="block text-sm font-medium mb-1">
								비밀번호
							</label>
							<input
								id="password"
								type="password"
								placeholder="********"
								value={password}
								onChange={e => setPassword(e.target.value)}
								required
								className="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 transition duration-200 border-gray-300 focus:ring-blue-500"
							/>
						</div>
						<motion.button
							whileHover={{scale: 1.05}}
							whileTap={{scale: 0.95}}
							type="submit"
							className="w-full bg-blue-600 text-white p-3 rounded-lg hover:bg-blue-700 transition duration-200 font-semibold"
						>
							로그인
						</motion.button>
					</form>
					<div className="mt-6">
						<div className="relative">
							<div className="absolute inset-0 flex items-center">
								<div className="w-full border-t border-gray-300"></div>
							</div>
							<div className="relative flex justify-center text-sm">
								<span className="px-2 bg-white text-gray-500">또는</span>
							</div>
						</div>
					</div>
					<p className="text-center text-sm mt-8 text-gray-600">
						계정이 없나요? <Link to={PATH.REGISTER} className="text-blue-500 hover:underline font-medium">계정
						만들기</Link>
					</p>
					{error && <p className="text-red-500 text-center mt-4">{error}</p>}
				</div>
			</motion.div>
		</>
	);
};

export default Login;