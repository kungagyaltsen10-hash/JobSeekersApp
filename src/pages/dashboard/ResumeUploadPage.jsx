import { useState } from 'react';
import Card from '../../components/ui/Card';
import Button from '../../components/ui/Button';
import { profileService } from '../../services/profileService';

function ResumeUploadPage() {
  const [file, setFile] = useState(null);
  const [status, setStatus] = useState('No file uploaded yet');
  const [uploading, setUploading] = useState(false);

  const handleUpload = async () => {
    if (!file) return;
    setUploading(true);
    setStatus('Uploading...');
    try {
      await profileService.uploadResume(file);
      setStatus(`Uploaded: ${file.name}`);
    } catch {
      setStatus(`Upload simulated: ${file.name}`);
    } finally {
      setUploading(false);
    }
  };

  return (
    <Card title="Resume Upload" subtitle="Upload your latest resume for AI parsing and matching.">
      <div className="space-y-4">
        <label className="flex min-h-48 cursor-pointer flex-col items-center justify-center rounded-2xl border-2 border-dashed border-slate-300 bg-slate-50 p-6 text-center transition hover:border-brand-500">
          <input
            type="file"
            className="hidden"
            accept=".pdf,.doc,.docx"
            onChange={(e) => setFile(e.target.files?.[0] || null)}
          />
          <p className="font-medium">Drag & drop resume here or click to select</p>
          <p className="mt-2 text-sm text-slate-500">Accepted formats: PDF, DOC, DOCX</p>
          {file && <p className="mt-3 text-sm text-brand-700">Selected: {file.name}</p>}
        </label>

        <div className="flex items-center justify-between gap-4">
          <p className="text-sm text-slate-600">{status}</p>
          <Button onClick={handleUpload} disabled={!file || uploading}>
            {uploading ? 'Uploading...' : 'Upload Resume'}
          </Button>
        </div>
      </div>
    </Card>
  );
}

export default ResumeUploadPage;
