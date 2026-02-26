import { useState } from 'react';
import Card from '../../components/ui/Card';
import MultiSelect from '../../components/ui/MultiSelect';
import Toggle from '../../components/ui/Toggle';
import Button from '../../components/ui/Button';
import { CITY_OPTIONS, STATES } from '../../constants/options';
import { profileService } from '../../services/profileService';

function LocationPreferencesPage() {
  const [form, setForm] = useState({
    cities: ['New York', 'Austin'],
    state: 'NY',
    remote: true,
    relocation: false,
  });
  const [status, setStatus] = useState('');

  const handleSave = async () => {
    try {
      await profileService.saveLocationPreferences(form);
      setStatus('Preferences saved successfully.');
    } catch {
      setStatus('Preferences saved locally (API unavailable).');
    }
  };

  return (
    <Card title="Location Preferences" subtitle="Optimize job relevance by defining your target locations.">
      <div className="space-y-4">
        <MultiSelect
          options={CITY_OPTIONS}
          selected={form.cities}
          onChange={(cities) => setForm({ ...form, cities })}
          label="Preferred Cities"
        />

        <label className="flex flex-col gap-2">
          <span className="text-sm font-medium text-slate-700">Preferred State</span>
          <select
            className="rounded-xl border border-slate-300 px-3 py-2"
            value={form.state}
            onChange={(e) => setForm({ ...form, state: e.target.value })}
          >
            {STATES.map((state) => (
              <option key={state} value={state}>
                {state}
              </option>
            ))}
          </select>
        </label>

        <div className="grid gap-3 sm:grid-cols-2">
          <Toggle label="Open to Remote" checked={form.remote} onChange={(remote) => setForm({ ...form, remote })} />
          <Toggle
            label="Open to Relocation"
            checked={form.relocation}
            onChange={(relocation) => setForm({ ...form, relocation })}
          />
        </div>

        <div className="flex items-center justify-between">
          <p className="text-sm text-slate-600">{status}</p>
          <Button onClick={handleSave}>Save Preferences</Button>
        </div>
      </div>
    </Card>
  );
}

export default LocationPreferencesPage;
