import { ExclamationOutlined } from '@ant-design/icons';
import { Button } from 'antd';
import React from 'react';
import { useNavigate } from 'react-router-dom'; // useNavigate로 변경

export const NotFound = () => {
	const navigate = useNavigate(); // useNavigate 사용
	const goBack = () => {
		navigate(-1); // 뒤로 가기
	};
	return (
		<div className="main-body-section">
			<div className="container">
				<div className="block-title" style={{ color: '#fa8c16' }}>
					<h2 style={{ color: '#fa8c16' }}>
						<ExclamationOutlined />
						Page Not Found
					</h2>
					<p className="large">Sorry, this page does not exist</p>
					<Button type="primary" onClick={goBack}>
						Go Back
					</Button>
				</div>
			</div>
		</div>
	);
};
