import React from 'react';
import SignUpForm from '../../components/Auth/SignUpForm';
import useSignUp from '../../hooks/useSignUp';

const SignUpPage: React.FC = () => {
	const {
		formData,
		handleChange,
		handleSubmit,
		handleEmailVerification,
		verifyEmailToken,
		checkNicknameAvailability,
		message,
	} = useSignUp();

	return (
		<div className="container mx-auto px-4">
			<SignUpForm
				formData={formData}
				handleChange={handleChange}
				handleSubmit={handleSubmit}
				handleEmailVerification={handleEmailVerification}
				verifyEmailToken={verifyEmailToken}
				checkNicknameAvailability={checkNicknameAvailability}
				message={message}
			/>
		</div>
	);
};

export default SignUpPage;