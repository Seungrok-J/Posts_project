import React from 'react';
import {NotFound} from "../../components/Error/404";
import {MainLayout} from "../layouts/MainLayout";


const _NotFoundPage = () => {
  return (
    <div>
      <MainLayout>
	      <NotFound/>
      </MainLayout>
    </div>
  );
};
const NotFoundPage = React.memo(_NotFoundPage);
export { NotFoundPage };
export default NotFoundPage;