import { RecoilRoot } from 'recoil';
// import { QueryClient, QueryClientProvider } from 'react-query';
import '@/styles/globals.css';
import Layout from '@/layouts/Layout';

function App({ Component, pageProps }) {
  return (
    <RecoilRoot>
      <Layout>
        <Component {...pageProps} />
      </Layout>
    </RecoilRoot>
  );
}

export default App;
