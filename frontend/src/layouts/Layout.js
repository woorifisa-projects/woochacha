import DefaultHeader from './DefaultHeader';
import DefaultFooter from './DefaultFooter';

// 기본 레이아웃()
export default function Layout({ children }) {
  return (
    <>
      <DefaultHeader />
      <div>{children}</div>
      <DefaultFooter />
    </>
  );
}
