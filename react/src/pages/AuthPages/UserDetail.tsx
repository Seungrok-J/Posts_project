import React from 'react';
import useUserStore from "../../store/useUserStore";

const UserDetail = () => {
	const { user } = useUserStore();
	console.log(user);

	return (
		<>
			<div>유저 상세 페이지</div>
			<div>{/* 유저 상세 정보를 여기에 표시할 수 있습니다 */}</div>
		</>
	);
};

export default UserDetail;
