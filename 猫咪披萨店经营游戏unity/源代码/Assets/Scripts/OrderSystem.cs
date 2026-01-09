using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using TMPro;
using UnityEngine.SceneManagement;

public class OrderSystem : MonoBehaviour
{
    public TMP_Text dialogueText;
    public Button acceptButton;
    public GameObject dialoguePanel;
    public CustomerController customerController;
    [SerializeField] private List <Order>presetOrders = new List <Order>();
    private Order pendingOrder;

    private void Start()
    {
        // 确保初始隐藏 DialoguePanel 和按钮
        dialoguePanel.SetActive(false);
        acceptButton.gameObject.SetActive(false);

        // 给按钮绑定点击事件
        acceptButton.onClick.AddListener(AcceptOrder);
    }
    
    private void Update()
    {
        // 检测回车键触发接受订单（仅在按钮可见时）
        if (acceptButton != null && acceptButton.gameObject.activeSelf)
        {
            if (Input.GetKeyDown(KeyCode.Return) || Input.GetKeyDown(KeyCode.KeypadEnter))
            {
                AcceptOrder();
            }
        }
    }
    //随机抽取一个订单
    private Order GetNextOrder(){
        if(presetOrders == null ||presetOrders.Count == 0){
            List <string> toppings = new List<string> { "lachang" };
            return new Order("Customer1",null, "Whitedough", "red", toppings, 5, 1);
        }
        int idx = Random.Range(0,presetOrders.Count);
        Order chosen = presetOrders[idx];
        return chosen;
    }

    // 预先准备下一个订单并设置顾客图片
    public void PrepareNextOrder()
    {
        pendingOrder = GetNextOrder();
        if(pendingOrder.customerSprite != null)
        {
            Debug.Log("Pre-setting customer image sprite");
            if(customerController != null)
            {
                customerController.SetCustomerImage(pendingOrder.customerSprite);
            }
        }
    }

    // 顾客到达目标位置时调用
    public void ShowOrder()
    {
        // 显示整个对话面板
        dialoguePanel.SetActive(true);

        // 只在顾客到达后显示按钮
        acceptButton.gameObject.SetActive(true);

        Order newOrder;
        if (pendingOrder != null)
        {
            newOrder = pendingOrder;
            pendingOrder = null;
        }
        else
        {
            newOrder = GetNextOrder();
            // 如果没有预设订单，这里补救设置一下图片
            if(newOrder.customerSprite != null && customerController != null)
            {
                customerController.SetCustomerImage(newOrder.customerSprite);
            }
        }

        dialogueText.text = $"I'd like {newOrder.pizzaCount} {newOrder.dough} pizza with {string.Join(", ", newOrder.toppings)}.";
        
        GameData.Instance.currentOrder = newOrder;
    }

    private void AcceptOrder()
    {
        // 点击后隐藏按钮，并更新对话
        acceptButton.gameObject.SetActive(false);
        dialogueText.text = "Great! I'll get started on that right away.";

        // 切换游戏状态到制作中
        GameManager.Instance.ChangeState(GameState.Making);
        // 统一通过 GameData 更新收入
        if (GameData.Instance != null && GameData.Instance.currentOrder != null)
        {
            GameData.Instance.AddRevenue(10 * GameData.Instance.currentOrder.pizzaCount);
        }
        SceneManager.LoadScene("PizzaMakingScene");
    }
}
