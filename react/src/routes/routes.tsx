import React, { lazy, Suspense } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Helmet, HelmetProvider } from 'react-helmet-async';
import { MainLayout } from '../pages/layouts/MainLayout';
import { Loading } from '../components/Loading';
import {PATH} from "../constants/paths";


// ---> Static Pages
const HomePage = lazy(() => import('../pages/Homepages/Home'));

// ---> Auth Pages
const LoginPage = lazy(() => import('../pages/AuthPages/Login'));
const RegisterPage = lazy(() => import('../pages/AuthPages/SignUp'));
const ProfilePage = lazy(() => import('../pages/AuthPages/UserDetail'));

// ---> Board Pages
const BoardPage = lazy(() => import('../pages/BoardPages/Board'))
const CreatePostPage = lazy(() => import('../pages/BoardPages/CreatePost'))
const EditPostPage = lazy(() => import('../pages/BoardPages/EditPost'))
const PostPage = lazy(() => import('../pages/BoardPages/Post'))


// ---> Error Pages
const NotFoundPage = lazy(() => import('../pages/ErrorPages/404Pages'));

const helmetContext = {};

export const AppRoutes = () => {
	return (
		<BrowserRouter>
			<HelmetProvider context={helmetContext}>
				<Helmet>
					<meta charSet="utf-8" />
					<title>My App</title>
				</Helmet>
				<MainLayout>
					<Suspense fallback={<Loading />}>
						<Routes>
							<Route path={PATH.HOME} element={<HomePage />} />
							<Route path={PATH.LOGIN} element={<LoginPage />} />
							<Route path={PATH.REGISTER} element={<RegisterPage />} />
							<Route path={PATH.PROFILE} element={<ProfilePage />} />
							<Route path={PATH.BOARD} element={<BoardPage />} />
							<Route path={PATH.POST_CRE} element={<CreatePostPage />} />
							<Route path={PATH.POST_EDIT} element={<EditPostPage />} />
							<Route path={PATH.POST} element={<PostPage />} />
							<Route path="*" element={<NotFoundPage />} />
						</Routes>
					</Suspense>
				</MainLayout>
			</HelmetProvider>
		</BrowserRouter>
	);
};
