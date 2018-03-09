package com.example.product.service;

import com.example.product.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Product> getProducts() {
        return productRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{productId}")
    public Product getProduct(@PathVariable Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (!product.isPresent()) {
            throw new ProductNotFoundException(productId);
        }
        return product.get();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{productId}/rules")
    public Collection<Rule> getRules(@PathVariable Long productId) {
        return getProduct(productId).getRules();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{productId}/rules/{ruleId}")
    public Rule getRule(@PathVariable Long productId, @PathVariable Long ruleId) {
        Optional<Rule> rule = getProduct(productId).getRules().stream()
                .filter(r -> r.getId().equals(ruleId))
                .findFirst();
        if (!rule.isPresent()) {
            throw new RuleNotFoundException(ruleId);
        }
        return rule.get();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{productId}/rules")
    public Rule addRule(@PathVariable Long productId, @Valid @RequestBody Rule addRuleRequest) {
        Rule rule;
        try {
            rule = new Rule(addRuleRequest);
        } catch (IllegalArgumentException e) {
            throw new IncorrectRuleException(e.getMessage());
        }
        Product product = getProduct(productId);
        if (product.getRules().contains(rule)) {
            throw new DuplicatedRuleException(product.findEqualRule(rule).get().getId());
        }
        product.getRules().add(rule);
        productRepository.save(product);
        return product.findEqualRule(rule).get();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{productId}/rules/{ruleId}")
    public void deleteRule(@PathVariable Long productId, @PathVariable Long ruleId) {
        Product product = getProduct(productId);
        product.getRules().removeIf(rule -> rule.getId().equals(ruleId));
        productRepository.save(product);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/apply")
    public Collection<Product> getApplicableProducts(@Valid @RequestBody ClientInfo info) {
        return getProducts().stream()
                .filter(product -> product.isApplicable(info))
                .collect(Collectors.toList());
    }

}
