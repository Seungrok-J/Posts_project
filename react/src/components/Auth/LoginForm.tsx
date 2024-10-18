import React from 'react';
import { motion } from 'framer-motion';
import { Link } from 'react-router-dom';
import { PATH } from '../../constants/paths';
import {LoginFormProps} from "../../@types/formTypes";


const LoginForm: React.FC<LoginFormProps> = ({ email, setEmail, password, setPassword, handleSubmit, error }) => {
	return (
		<form onSubmit={handleSubmit} className="space-y-6">
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
			{error && <p className="text-red-500 text-center mt-4">{error}</p>}
			<p className="text-center text-sm mt-8 text-gray-600">
				계정이 없나요? <Link to={PATH.REGISTER} className="text-blue-500 hover:underline font-medium">계정 만들기</Link>
			</p>
		</form>
	);
};

export default LoginForm;