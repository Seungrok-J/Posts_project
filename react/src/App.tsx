import React from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import Homepage from './pages/Home'
import BoardPage from './pages/Board'
import UserDetail from "./pages/UserDetail";
import Header from "./components/common/Header";
import Footer from "./components/common/Footer";
import Nav from "./components/common/Nav";
import CreatePostPage from "./pages/CreatePost";
import EditPostPage from "./pages/EditPost";
import LoginPage from "./pages/Login";
import PostPage from "./pages/Post";
import SignUpPage from "./pages/SignUp";

function App() {
	return (
		<Router>
			<Header/>
			<Nav/>
			<Routes>
				<Route path="/" element={<Homepage/>}/>
				<Route path="/login" element={<LoginPage/>}/>
				<Route path="/signup" element={<SignUpPage/>}/>
				<Route path="/user/:id" element={<UserDetail/>}/>
				<Route path="/board" element={<BoardPage/>}/>
				<Route path="/post/:id" element={<PostPage/>}/>
				<Route path="/post/create" element={<CreatePostPage/>}/>
				<Route path="/post/edit/:id" element={<EditPostPage/>}/>
			</Routes>
			<Footer/>
		</Router>
	);
}

export default App;
