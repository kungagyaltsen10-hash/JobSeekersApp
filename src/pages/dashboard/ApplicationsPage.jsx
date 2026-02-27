import { useEffect, useMemo, useState } from 'react';
import Card from '../../components/ui/Card';
import Badge from '../../components/ui/Badge';
import { APPLICATION_STATUSES } from '../../constants/options';
import { jobService } from '../../services/jobService';
import { mockApplications } from './mockData';

function ApplicationsPage() {
  const [applications, setApplications] = useState(mockApplications);
  const [cityFilter, setCityFilter] = useState('All');
  const [statusFilter, setStatusFilter] = useState('All');

  useEffect(() => {
    const fetchApplications = async () => {
      try {
        const data = await jobService.getApplications();
        setApplications(data.applications || data);
      } catch {
        // fallback
      }
    };
    fetchApplications();
  }, []);

  const cities = useMemo(() => ['All', ...new Set(applications.map((item) => item.city))], [applications]);

  const filtered = useMemo(
    () =>
      applications.filter((app) => {
        const cityMatch = cityFilter === 'All' || app.city === cityFilter;
        const statusMatch = statusFilter === 'All' || app.status === statusFilter;
        return cityMatch && statusMatch;
      }),
    [applications, cityFilter, statusFilter]
  );

  return (
    <Card title="Applications Tracker" subtitle="Monitor progress and outcomes for auto-submitted jobs.">
      <div className="mb-4 grid gap-3 sm:grid-cols-2">
        <select
          className="rounded-xl border border-slate-300 px-3 py-2"
          value={cityFilter}
          onChange={(e) => setCityFilter(e.target.value)}
        >
          {cities.map((city) => (
            <option key={city} value={city}>
              {city}
            </option>
          ))}
        </select>

        <select
          className="rounded-xl border border-slate-300 px-3 py-2"
          value={statusFilter}
          onChange={(e) => setStatusFilter(e.target.value)}
        >
          {['All', ...APPLICATION_STATUSES].map((status) => (
            <option key={status} value={status}>
              {status}
            </option>
          ))}
        </select>
      </div>

      <div className="overflow-x-auto">
        <table className="w-full min-w-[560px] text-left text-sm">
          <thead>
            <tr className="border-b border-slate-200 text-slate-500">
              <th className="pb-3">Role</th>
              <th className="pb-3">Company</th>
              <th className="pb-3">City</th>
              <th className="pb-3">Status</th>
            </tr>
          </thead>
          <tbody>
            {filtered.map((item) => (
              <tr key={item.id} className="border-b border-slate-100">
                <td className="py-3 font-medium">{item.role}</td>
                <td className="py-3">{item.company}</td>
                <td className="py-3">{item.city}</td>
                <td className="py-3">
                  <Badge
                    tone={
                      item.status === 'Offer'
                        ? 'success'
                        : item.status === 'Rejected'
                          ? 'danger'
                          : item.status === 'Interviewing'
                            ? 'info'
                            : 'warning'
                    }
                  >
                    {item.status}
                  </Badge>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </Card>
  );
}

export default ApplicationsPage;
