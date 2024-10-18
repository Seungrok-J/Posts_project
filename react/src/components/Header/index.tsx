import React from "react";
import {Link} from "react-router-dom";
import { PATH } from "../../constants/paths";
import useUserStore from "../../store/useUserStore";
import useLogout from "../../hooks/useLogout";

const AppHeader: React.FC = () => {
    const { isLoggedIn, user } = useUserStore();

    const handleLogout = useLogout();

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
                    {isLoggedIn && user ? (
                        <>
                            <li className="mr-6">
                                <Link className="text-white hover:text-gray-300" to={`/user/${user.userId}`}>User
                                    Profile</Link>
                            </li>
                            <li>
                                <button
                                    className="py-2 px-4 bg-blue-500 hover:bg-blue-700 text-white font-semibold rounded-lg shadow-md"
                                    onClick={handleLogout}
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