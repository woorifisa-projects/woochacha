import { RecoilRoot } from 'recoil';
// import { QueryClient, QueryClientProvider } from 'react-query';
import '@/styles/globals.css';
import Layout from '@/layouts/Layout';

function App({ Component, pageProps }) {
  const EmptyLayout = ({ children }) => <>{children}</>;
  const NestedLayout = Component.Layout || EmptyLayout;

  return (
    <RecoilRoot>
      <Layout>
        <NestedLayout>
          <Component {...pageProps} />
        </NestedLayout>
      </Layout>
    </RecoilRoot>
  );
}

export default App;
