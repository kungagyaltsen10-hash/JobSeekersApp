import { useEffect, useState } from 'react';
import Card from '../../components/ui/Card';
import StatCard from '../../components/common/StatCard';
import { dashboardService } from '../../services/dashboardService';
import { mockSummary } from './mockData';

function DashboardPage() {
  const [summary, setSummary] = useState(mockSummary);

  useEffect(() => {
    const fetchSummary = async () => {
      try {
        const data = await dashboardService.getSummary();
        setSummary(data);
      } catch {
        // fallback to mock data
      }
    };
    fetchSummary();
  }, []);

  return (
    <div className="space-y-6">
      <Card title="Profile Summary" subtitle="Keep your profile complete for better match quality.">
        <div className="flex flex-col gap-2 sm:flex-row sm:items-center sm:justify-between">
          <div>
            <p className="text-xl font-semibold">{summary.profile.name}</p>
            <p className="text-slate-500">{summary.profile.title}</p>
          </div>
          <p className="text-sm text-slate-600">Completion: {summary.profile.completion}%</p>
        </div>
      </Card>

      <div className="grid gap-4 md:grid-cols-2">
        <Card title="Resume Status">
          <p className="text-slate-600">{summary.resumeStatus}</p>
        </Card>
        <Card title="Location Preferences">
          <p className="text-slate-600">{summary.locationPreferences}</p>
        </Card>
      </div>

      <section>
        <h2 className="mb-3 text-lg font-semibold">Match Analytics</h2>
        <div className="grid gap-4 sm:grid-cols-2 xl:grid-cols-4">
          {summary.analytics.map((item) => (
            <StatCard key={item.label} label={item.label} value={item.value} hint={item.hint} />
          ))}
        </div>
      </section>
    </div>
  );
}

export default DashboardPage;
