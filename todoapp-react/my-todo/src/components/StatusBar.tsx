interface Props {
  clearAll: () => void;
  left: number;
}

function StatusBar({ clearAll, left }: Props) {
  return (
    <div className="status-bar">
      <span className="status-left">
        <span className="count">{left} </span>tasks left
      </span>
      <button onClick={clearAll}>Clear All</button>
    </div>
  );
}
export default StatusBar;
