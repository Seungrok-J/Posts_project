import React from "react";
import {Link} from "react-router-dom";
import {PATH} from "../../constants/paths";

interface HeaderProps {
    isLoggedIn: boolean;
    onLogout: () => void;
}

const AppHeader: React.FC<HeaderProps> = ({isLoggedIn, onLogout}) => {
    return (
        <header className="bg-gray-800 text-white p-4">
            <nav>
                <ul className="flex justify-between items-center">
                    <li className="mr-6">
                        <Link className="text-white hover:text-gray-300" to={PATH.HOME}>Home</Link>

                    </li>
                    <li className="mr-6">
                        <Link className="text-white hover:text-gray-300" to={PATH.BOARD}>Board</Link>
                    </li>
                    {isLoggedIn ? (
                        <>
                            <li className="mr-6">
                                <Link className="text-white hover:text-gray-300" to={PATH.PROFILE}>User
                                    Profile</Link>
                            </li>
                            <li>
                                <button
                                    className="py-2 px-4 bg-blue-500 hover:bg-blue-700 text-white font-semibold rounded-lg shadow-md"
                                    onClick={onLogout}
                                >
                                    Logout
                                </button>
                            </li>
                        </>
                    ) : (
                        <>
                            <li className="mr-6">
                                <Link className="text-white hover:text-gray-300" to={PATH.LOGIN}>Login</Link>
                            </li>
                            <li>
                                <Link
                                    className="py-2 px-4 bg-green-500 hover:bg-green-700 text-white font-semibold rounded-lg shadow-md"
                                    to={PATH.REGISTER}
                                >
                                    Sign Up
                                </Link>
                            </li>
                        </>
                    )}
                </ul>
            </nav>
        </header>
    );
};

export default AppHeader;
