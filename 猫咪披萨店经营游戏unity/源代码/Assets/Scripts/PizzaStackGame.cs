using System.Collections;
using System.Collections.Generic;
using Unity.VisualScripting;
using UnityEngine;

public class PizzaStackGame : MonoBehaviour
{
    public FailVideoController failVideoController;
    public SuccessVideoController successVideoController;
    public RectTransform boxPrefab;
    public RectTransform stackPoint;
    public RectTransform startPoint;
    public float moveSpeed = 2f;
    public float moveRange = 200f;
    public float collapseThreshold = 2f;
    public float boxHeight = 30f;
    public int totalBoxCount = 5;

    private RectTransform currentBox;
    private RectTransform previousBox;
    public RectTransform uiParent;
    private float totalOffset = 0f;
    private bool GameOver = false;
    private bool GameStarted = false;
    private int placedBoxCount = 0; // 已放置的盒子数量

    // Update is called once per frame
    void Start()
    {
        if (GameData.Instance != null && GameData.Instance.currentOrder != null)
        {
            totalBoxCount = GameData.Instance.currentOrder.pizzaCount;
        }
    }
    void Update()
    {
        if(!GameStarted || GameOver)return;

        if(currentBox != null)
        {
            float x = Mathf.PingPong(Time.time * moveSpeed, moveRange*2) - moveRange;
            currentBox.anchoredPosition = new Vector2(x, startPoint.anchoredPosition.y);
        }
        if(Input.GetKeyDown(KeyCode.Space)&& currentBox != null)
        {
            PlaceBox();
        }
    }
    public void StartGame()
    {
        GameStarted = true;
        totalOffset = 0f;
        previousBox = null;
        placedBoxCount = 0; // 重置已放置的盒子数量
        GameOver = false;
        SpawnNewBox();
        Debug.Log("Pizza Stacking Game Started!");
    }
    void SpawnNewBox()
    {
        Debug.Log("Spawn New Box");
        currentBox = Instantiate(boxPrefab, uiParent);
        currentBox.anchoredPosition = startPoint.anchoredPosition;
    }

    void PlaceBox()
    {
        Debug.Log("Place Box");

        Vector2 targetPos = stackPoint.anchoredPosition;

        if (previousBox != null)
        {
            // 使用 UI 高度叠加
            targetPos.y = previousBox.anchoredPosition.y + boxHeight;
        }

        currentBox.anchoredPosition =
            new Vector2(currentBox.anchoredPosition.x, targetPos.y);

        if (previousBox != null)
        {
            float offset =
                currentBox.anchoredPosition.x - previousBox.anchoredPosition.x;

            totalOffset += Mathf.Abs(offset);

            if (totalOffset > collapseThreshold)
            {
                GameOver = true;
                Debug.Log("Game Over!");
                failVideoController.PlayFailVideo();
                return;
            }
        }

        previousBox = currentBox;
        currentBox = null;
        placedBoxCount++; // 增加已放置的盒子数量

        // 检查是否达到目标数量
        if (placedBoxCount >= totalBoxCount)
        {
            GameOver = true;
            Debug.Log("Game Over! You win!");
            successVideoController.PlaySuccessVideo();
            
            // 统一通过 GameData 更新收入
            if (GameData.Instance != null)
            {
                GameData.Instance.AddRevenue(5);
            }
            return;
        }

        // 如果还没达到目标，生成下一个盒子
        SpawnNewBox();
    }

}
