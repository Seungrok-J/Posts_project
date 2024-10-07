import React from 'react';
import {Link} from "react-router-dom";

const Nav = () => {
	return (
		<nav className="p-4 shadow-md transition-colors duration-300">
			<div className="container mx-auto flex justify-between items-center">
				<Link to="/" className="text-xl font-bold hover:text-blue-500 transition-colors">
					Home
				</Link>
				<Link to="/login"
				      className="bg-blue-600 hover:bg-blue-700 text-white py-2 px-4 rounded text-sm shadow transition-colors">
					Login
				</Link>
				<Link to="/signup"
				      className="bg-blue-600 hover:bg-blue-700 text-white py-2 px-4 rounded text-sm shadow transition-colors">
					SignUp
				</Link>
			</div>
		</nav>
	);
};

export default Nav;