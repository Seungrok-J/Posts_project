import React, {ReactNode, useState} from 'react';
import AppHeader from '../../components/Header';
import AppFooter from '../../components/Footer';
import {Layout} from 'antd';

const {Content} = Layout;

interface Props {
	children: ReactNode;
}

export const MainLayout = (props: Props) => {
	const {children} = props;
	// 로그인 상태를 useState 훅으로 관리합니다.
	const [isLoggedIn, setIsLoggedIn] = useState(false);

	// 로그아웃 함수
	const handleLogout = () => {
		setIsLoggedIn(false);
		// 실제 애플리케이션에서는 여기에 로그아웃 로직 (예: API 호출)을 추가할 수 있습니다.
	};

	return (
		<Layout>
			<AppHeader isLoggedIn={isLoggedIn} onLogout={handleLogout}/>
			<Content>
				{children}
			</Content>

			<AppFooter/>
		</Layout>

	);
};
