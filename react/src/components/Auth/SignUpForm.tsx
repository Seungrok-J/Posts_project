import React from 'react';
import { SignUpFormProps } from '../../@types/formTypes';

const SignUpForm: React.FC<SignUpFormProps> = ({
	                                               formData,
	                                               handleChange,
	                                               handleSubmit,
	                                               handleEmailVerification,
	                                               verifyEmailToken,
	                                               checkNicknameAvailability,
	                                               message,
                                               }) => {
	return (
		<div className="container mx-auto px-4">
			<form onSubmit={handleSubmit} className="mt-8 max-w-md mx-auto">
				{message && <div className="mb-4 text-center font-medium text-red-600">{message}</div>}
				<div className="mb-6">
					<label htmlFor="userName" className="block mb-2 text-sm font-medium text-gray-900">User
						Name</label>
					<input type="text" id="userName" name="userName" required
					       className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
					       placeholder="User Name" value={formData.userName} onChange={handleChange}/>
				</div>
				<div className="mb-6">
					<label htmlFor="nickName" className="block mb-2 text-sm font-medium text-gray-900">Nick
						Name</label>
					<input type="text" id="nickName" name="nickName" required
					       className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
					       placeholder="Nick Name" value={formData.nickName} onChange={handleChange}/>
					<button type="button" onClick={checkNicknameAvailability}
					        className="text-white bg-blue-500 hover:bg-blue-600 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2.5 mr-2">
						Check Nickname
					</button>
				</div>
				<div className="mb-6">
					<label htmlFor="userEmail"
					       className="block mb-2 text-sm font-medium text-gray-900">Email</label>
					<input type="email" id="userEmail" name="userEmail" required
					       className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
					       placeholder="Email" value={formData.userEmail} onChange={handleChange}/>
					<button type="button" onClick={handleEmailVerification}
					        className="text-white bg-green-500 hover:bg-green-600 focus:ring-4 focus:ring-green-300 font-medium rounded-lg text-sm px-4 py-2.5">
						Verify Email
					</button>
				</div>
				<div className="mb-6">
					<label htmlFor="verificationCode" className="block mb-2 text-sm font-medium text-gray-900">Verification
						Code</label>
					<input type="text" id="verificationCode" name="verificationCode"
					       className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
					       placeholder="Enter your verification code" value={formData.verificationCode}
					       onChange={handleChange}/>
					<button type="button" onClick={() => verifyEmailToken(formData.verificationCode)}
					        className="text-white bg-green-500 hover:bg-green-600 focus:ring-4 focus:ring-green-300 font-medium rounded-lg text-sm px-4 py-2.5">
						Verify Code
					</button>
				</div>


				<div className="mb-6">
					<label htmlFor="password"
					       className="block mb-2 text-sm font-medium text-gray-900">Password</label>
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
};

export default SignUpForm;