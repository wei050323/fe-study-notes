using UnityEngine;
using UnityEngine.Video;

public class SuccessVideoController : MonoBehaviour
{
    public VideoPlayer videoPlayer;
    public GameObject successVideoRoot; // RawImage 对象
    public System.Action onVideoEnd; // 视频结束回调

    void Awake()
    {
        videoPlayer.loopPointReached += OnVideoEnd;
        successVideoRoot.SetActive(false);
    }

    // 播放成功视频
    public void PlaySuccessVideo()
    {
        successVideoRoot.SetActive(true);
        videoPlayer.Stop();
        videoPlayer.Play();
    }

    // 视频播放完毕
    private void OnVideoEnd(VideoPlayer vp)
    {
        successVideoRoot.SetActive(false);
        onVideoEnd?.Invoke();
    }
}
