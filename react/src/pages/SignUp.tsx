import React, {useState, ChangeEvent, FormEvent} from 'react';

function SignUpPage() {
	const [formData, setFormData] = useState({
		userName: '',
		nickName: '',
		userEmail: '',
		password: '',
		confirmPassword: '',  // 비밀번호 확인을 위한 상태 추가
		role: ''
	});

	const [emailVerified, setEmailVerified] = useState(false);  // 이메일 인증 상태
	const [nicknameAvailable, setNicknameAvailable] = useState(false);  // 닉네임 사용 가능 상태

	const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
		const {name, value} = e.target;
		setFormData(prevState => ({
			...prevState,
			[name]: value
		}));
	};

	const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
		e.preventDefault();
		if (formData.password !== formData.confirmPassword) {
			alert('Passwords do not match!');
			return;
		}
		if (!emailVerified) {
			alert('Please verify your email.');
			return;
		}
		if (!nicknameAvailable) {
			alert('Nickname is taken, please choose another one.');
			return;
		}
		console.log(formData);
		alert('Registration successful!');
	};

	const handleEmailVerification = () => {
		// 이메일 인증 로직을 여기에 구현하세요
		console.log('Email verification sent to:', formData.userEmail);
		setEmailVerified(true);  // 예시로 세팅, 실제로는 인증 후에 세팅해야 함
	};

	const checkNicknameAvailability = () => {
		// 닉네임 중복 확인 로직을 여기에 구현하세요
		console.log('Checking nickname availability for:', formData.nickName);
		setNicknameAvailable(true);  // 예시로 세팅, 실제로는 API 확인 후에 세팅해야 함
	};

	return (
		<div className="container mx-auto px-4">
			<form onSubmit={handleSubmit} className="mt-8 max-w-md mx-auto">
				<div className="mb-6">
					<label htmlFor="userName" className="block mb-2 text-sm font-medium text-gray-900">User Name</label>
					<input type="text" id="userName" name="userName" required
					       className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
					       placeholder="User Name" value={formData.userName} onChange={handleChange}/>
				</div>
				<div className="mb-6">
					<label htmlFor="nickName" className="block mb-2 text-sm font-medium text-gray-900">Nick Name</label>
					<input type="text" id="nickName" name="nickName" required
					       className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
					       placeholder="Nick Name" value={formData.nickName} onChange={handleChange}/>
					<button type="button" onClick={checkNicknameAvailability}
					        className="text-white bg-blue-500 hover:bg-blue-600 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2.5 mr-2">
						Check Nickname
					</button>
				</div>
				<div className="mb-6">
					<label htmlFor="userEmail" className="block mb-2 text-sm font-medium text-gray-900">Email</label>
					<input type="email" id="userEmail" name="userEmail" required
					       className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
					       placeholder="Email" value={formData.userEmail} onChange={handleChange}/>

					<button type="button" onClick={handleEmailVerification}
					        className="text-white bg-green-500 hover:bg-green-600 focus:ring-4 focus:ring-green-300 font-medium rounded-lg text-sm px-4 py-2.5">
						Verify Email
					</button>
				</div>

				<div className="mb-6">
					<label htmlFor="password" className="block mb-2 text-sm font-medium text-gray-900">Password</label>
					<input type="password" id="password" name="password" required
					       className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
					       placeholder="Password" value={formData.password} onChange={handleChange}/>
				</div>
				<div className="mb-6">
					<label htmlFor="confirmPassword" className="block mb-2 text-sm font-medium text-gray-900">Confirm
						Password</label>
					<input type="password" id="confirmPassword" name="confirmPassword" required
					       className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
					       placeholder="Confirm Password" value={formData.confirmPassword} onChange={handleChange}/>
				</div>
				<button type="submit"
				        className="text-white bg-blue-500 hover:bg-blue-600 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center">
					Sign up
				</button>
			</form>
		</div>
	);
}

export default SignUpPage;
