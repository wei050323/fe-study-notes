using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using TMPro;
using UnityEngine.Video;
using UnityEngine.SceneManagement;
using System;
using Unity.VisualScripting;

public class PizzaMakingManager : MonoBehaviour
{
    public BakingVideoController bakingVideoController;
    
    public GameObject prepAreaRoot;
    public GameObject ovenAndStackAreaRoot;
    public PizzaStackGame pizzaStackGame;
    //披萨饼皮
    public GameObject doughWhite;
    public GameObject doughBlack;
    //披萨酱料
    public GameObject sauceRed;
    public GameObject sauceGreen;
    //披萨配料
    public GameObject[] toppingOnPizza;
    // 选中的配料图标
    public GameObject[] toppingSelectedIcons;
    public string[] toppingNames; // 对应topping索引的名称，如["lachang", "pepperoni", "mushroom", "olive"]
    public int totalPizzaCount = 0;
    private int finishedPizzaCount = 0;
    
    private MakingStep step = MakingStep.SelectDough;
    private int doughIndex = -1;
    private int sauceIndex = -1;
    private HashSet<int> selectedToppings = new HashSet<int>();
    private enum MakingStep
    {
        SelectDough,
        SelectSauce,
        AddToppings,
        showResult,
        Done,
        Baking,
        Stacking,
        Finish
    }
    void Start()
    {
        // 从订单获取比萨数量
        if (GameData.Instance != null && GameData.Instance.currentOrder != null)
        {
            totalPizzaCount = GameData.Instance.currentOrder.pizzaCount;
        }
        finishedPizzaCount = 0;
        
        doughWhite.SetActive(false);
        doughBlack.SetActive(false);
        sauceRed.SetActive(false);
        sauceGreen.SetActive(false);
        foreach (var topping in toppingOnPizza)
        {
            topping.SetActive(false);
        }
        foreach (var icon in toppingSelectedIcons)
        {
            icon.SetActive(false);
        }
        ovenAndStackAreaRoot.SetActive(false);
    }
    void Update()
    {
        if(Input.GetKeyDown(KeyCode.Backspace))
        {
            DiscardCurrentPizza();
        }
        switch (step)
        {
            case MakingStep.SelectDough:
                // 等待玩家选择面团
                HandleDough();
                break;
            case MakingStep.SelectSauce:
                // 等待玩家选择酱料
                HandleSauce();
                break;
            case MakingStep.AddToppings:
                // 等待玩家添加配料
                HandleToppings();
                break;
            case MakingStep.showResult:
                if (Input.anyKeyDown)
                {
                    FinishOnePizza();
                }
                break;
            case MakingStep.Done:
                EnterBakingPhase();
                step = MakingStep.Baking;
                break;
            case MakingStep.Baking:
                bakingVideoController.PlayBakingVideo();
                step = MakingStep.Stacking;
                break;
            case MakingStep.Stacking:
                EnterStackGame();
                step = MakingStep.Finish;
                break;
                // Stacking logic can be added here
        }
    }
    private void EnterBakingPhase()
    {
        prepAreaRoot.SetActive(false);
        ovenAndStackAreaRoot.SetActive(true);
    }
    void EnterStackGame()
    {
        pizzaStackGame.StartGame();
    }
    void HandleDough()
    {
        if (Input.GetKeyDown(KeyCode.UpArrow))
        {
            doughIndex = 0;
            doughWhite.SetActive(true);
            step = MakingStep.SelectSauce;
        }
        else if (Input.GetKeyDown(KeyCode.DownArrow))
        {
            doughIndex = 1;
            doughBlack.SetActive(true);
            step = MakingStep.SelectSauce;
        }
    }
    void HandleSauce()
    {
        if (Input.GetKeyDown(KeyCode.LeftArrow))
        {
            sauceIndex = 0;
            sauceRed.SetActive(true);
            step = MakingStep.AddToppings;
        }
        else if (Input.GetKeyDown(KeyCode.RightArrow))
        {
            sauceIndex = 1;
            sauceGreen.SetActive(true);
            step = MakingStep.AddToppings;
        }
    }
    void HandleToppings()
    {
        if(Input.GetKeyDown(KeyCode.Alpha1))ToggleTopping(0);
        if(Input.GetKeyDown(KeyCode.Alpha2))ToggleTopping(1);
        if(Input.GetKeyDown(KeyCode.Alpha3))ToggleTopping(2);
        if(Input.GetKeyDown(KeyCode.Alpha4))ToggleTopping(3);
        
        if(Input.GetKeyDown(KeyCode.Return))
        {
            ApplyToppings();
            step = MakingStep.showResult;
        }
    }
    void ToggleTopping(int index)
    {
        if (selectedToppings.Contains(index))
        {
            selectedToppings.Remove(index);
            toppingSelectedIcons[index].SetActive(false);
        }
        else
        {
            selectedToppings.Add(index);
            toppingSelectedIcons[index].SetActive(true);
        }
    }
    void ApplyToppings()
    {
        foreach (int index in selectedToppings)
        {
            toppingOnPizza[index].SetActive(true);
        }
    }
   
    void FinishOnePizza()
    {
        finishedPizzaCount++;
        Debug.Log($"完成比萨：{finishedPizzaCount}/{totalPizzaCount}");
        if(finishedPizzaCount<totalPizzaCount)
        {
            ResetMakingForNextPizza();
        }
        else
        {
            step = MakingStep.Done;
            Debug.Log("订单完成！");
        }
    }
    void ResetMakingForNextPizza()
    {
        doughWhite.SetActive(false);
        doughBlack.SetActive(false);
        sauceRed.SetActive(false);
        sauceGreen.SetActive(false);
        foreach (var topping in toppingOnPizza)
        {
            topping.SetActive(false);
        }
        foreach(var icon in toppingSelectedIcons)
        {
            icon.SetActive(false);
        }
        doughIndex = -1;
        sauceIndex = -1;
        selectedToppings.Clear();
        step = MakingStep.SelectDough;
    }

    void DiscardCurrentPizza()
    {
        doughWhite.SetActive(false);
        doughBlack.SetActive(false);
        sauceRed.SetActive(false);
        sauceGreen.SetActive(false);
        foreach (var topping in toppingOnPizza)
        {
            topping.SetActive(false);
        }
        foreach(var icon in toppingSelectedIcons)
        {
            icon.SetActive(false);
        }
        doughIndex = -1;
        sauceIndex = -1;
        selectedToppings.Clear();
        step = MakingStep.SelectDough;
        Debug.Log("当前比萨已丢弃，重新制作该比萨");
    }

}
