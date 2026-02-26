import { Navigate, Route, Routes } from 'react-router-dom';
import MainLayout from '../components/layout/MainLayout';
import ProtectedRoute from './ProtectedRoute';
import LoginPage from '../pages/auth/LoginPage';
import SignupPage from '../pages/auth/SignupPage';
import DashboardPage from '../pages/dashboard/DashboardPage';
import ResumeUploadPage from '../pages/dashboard/ResumeUploadPage';
import LocationPreferencesPage from '../pages/dashboard/LocationPreferencesPage';
import JobMatchesPage from '../pages/dashboard/JobMatchesPage';
import ApplicationsPage from '../pages/dashboard/ApplicationsPage';

function AppRoutes() {
  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route path="/signup" element={<SignupPage />} />
      <Route
        path="/"
        element={
          <ProtectedRoute>
            <MainLayout />
          </ProtectedRoute>
        }
      >
        <Route index element={<Navigate to="/dashboard" replace />} />
        <Route path="dashboard" element={<DashboardPage />} />
        <Route path="resume" element={<ResumeUploadPage />} />
        <Route path="locations" element={<LocationPreferencesPage />} />
        <Route path="matches" element={<JobMatchesPage />} />
        <Route path="applications" element={<ApplicationsPage />} />
      </Route>
      <Route path="*" element={<Navigate to="/dashboard" replace />} />
    </Routes>
  );
}

export default AppRoutes;
