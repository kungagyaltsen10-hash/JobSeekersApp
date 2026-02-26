import Badge from '../ui/Badge';

function ConfidenceIndicator({ confidence }) {
  const map = {
    Low: 'warning',
    Medium: 'info',
    High: 'success',
  };

  return <Badge tone={map[confidence] || 'default'}>{confidence} confidence</Badge>;
}

export default ConfidenceIndicator;
