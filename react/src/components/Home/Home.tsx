import React from 'react';
import {Link} from 'react-router-dom';
import {PATH} from '../../constants/paths';
import useUserStore from '../../store/useUserStore'

const HomePage: React.FC = () => {
	const {user, isLoggedIn} = useUserStore();
	console.log("user",user)

	return (
		<div className="bg-gray-900 min-h-screen flex items-center justify-center text-white">
			<div className="bg-black bg-opacity-50 p-8 rounded-lg">
				<div className="max-w-xl">
					<h1 className="text-4xl font-bold mb-4">React Typescript Template</h1>
					{isLoggedIn && user ? (
						<>
							<p>Nick Name : {user.nickName}</p>
							<p className="text-gray-300">
								User Name: <span className="ml-2">{user.userName}</span>
							</p>
							<p className="text-gray-300">
								ROLE: <span className="ml-2">{user.role}</span>
							</p>
							<div className="mt-6">
								<Link to={PATH.BOARD}
								      className="inline-block bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
									Go To Board Page
								</Link>

							</div>
						</>
					) : (
						<>
							<p>Please login with account & password below.</p>
							<p className="text-gray-300">
								Account:<span className="ml-2">tester@tester</span>
							</p>
							<p className="text-gray-300">
								Password:<span className="ml-2">1111</span>
							</p>
							<div className="mt-6">
								<Link to={PATH.LOGIN}
								      className="inline-block bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
									Go To Login Page
								</Link>
							</div>
						</>
					)}
				</div>
			</div>
		</div>
	)
		;
};

export default HomePage;
