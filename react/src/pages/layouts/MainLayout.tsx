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

	return (
		<Layout>
			<AppHeader/>
			<Content>
				{children}
			</Content>

			<AppFooter/>
		</Layout>

	);
};